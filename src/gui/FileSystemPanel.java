package gui;

/**
 * All of the code here was taken from https://github.com/aterai/java-swing-tips.
 * We changed some of the code to make it fit our program
 */

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.*;

import utils.BinaryList;
import utils.CSVUtils;

public final class FileSystemPanel extends JPanel {

	public FileSystemPanel() {
		super(new BorderLayout());

		FileSystemView fileSystemView = FileSystemView.getFileSystemView();
		DefaultMutableTreeNode root = new DefaultMutableTreeNode();
		DefaultTreeModel treeModel = new DefaultTreeModel(root);
		for (File fileSystemRoot : fileSystemView.getRoots()) {
			DefaultMutableTreeNode node = new DefaultMutableTreeNode(
					new CheckBoxNode(fileSystemRoot, Status.DESELECTED));
			root.add(node);
			Arrays.stream(fileSystemView.getFiles(fileSystemRoot, true)).filter(File::isDirectory)
					.map(file -> new CheckBoxNode(file, Status.DESELECTED)).map(DefaultMutableTreeNode::new)
					.forEach(node::add);
		}
		// treeModel.addTreeModelListener(new CheckBoxStatusUpdateListener());

		JTree tree = new JTree(treeModel) {
			@Override
			public void updateUI() {
				setCellRenderer(null);
				setCellEditor(null);
				super.updateUI();
				setCellRenderer(new FileTreeCellRenderer(fileSystemView));
				setCellEditor(new CheckBoxNodeEditor(fileSystemView));
			}
		};
		tree.setRootVisible(false);
		tree.addTreeSelectionListener(new FolderSelectionListener(fileSystemView));

		tree.setEditable(true);
		tree.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));

		tree.expandRow(0);

		setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		add(new JScrollPane(tree));

		setPreferredSize(new Dimension(280, 300));
	}
}

class TriStateCheckBox extends JCheckBox {
	@Override
	public void updateUI() {
		Icon currentIcon = getIcon();
		setIcon(null);
		super.updateUI();
		if (Objects.nonNull(currentIcon)) {
			setIcon(new IndeterminateIcon());
		}
		setOpaque(false);
	}
}

class IndeterminateIcon implements Icon {
	private static final Color FOREGROUND = new Color(50, 20, 255, 200); // TEST:
																			// UIManager.getColor("CheckBox.foreground");
	private static final int SIDE_MARGIN = 4;
	private static final int HEIGHT = 2;
	private final Icon icon = UIManager.getIcon("CheckBox.icon");

	@Override
	public void paintIcon(Component c, Graphics g, int x, int y) {
		int w = getIconWidth();
		int h = getIconHeight();
		Graphics2D g2 = (Graphics2D) g.create();
		g2.translate(x, y);
		icon.paintIcon(c, g2, 0, 0);
		g2.setPaint(FOREGROUND);
		g2.fillRect(SIDE_MARGIN, (h - HEIGHT) / 2, w - SIDE_MARGIN - SIDE_MARGIN, HEIGHT);
		g2.dispose();
	}

	@Override
	public int getIconWidth() {
		return icon.getIconWidth();
	}

	@Override
	public int getIconHeight() {
		return icon.getIconHeight();
	}
}

enum Status {
	SELECTED, DESELECTED, INDETERMINATE
}

class CheckBoxNode {
	public final File file;
	public final Status status;

	protected CheckBoxNode(File file) {
		this.file = file;
		status = Status.INDETERMINATE;
	}

	protected CheckBoxNode(File file, Status status) {
		this.file = file;
		this.status = status;
	}

	@Override
	public String toString() {
		return file.getName();
	}
}

class FileTreeCellRenderer implements TreeCellRenderer {
	private final JPanel panel = new JPanel(new BorderLayout());
	private final DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
	private final TriStateCheckBox checkBox = new TriStateCheckBox();
	private final FileSystemView fileSystemView;

	protected FileTreeCellRenderer(FileSystemView fileSystemView) {
		super();
		this.fileSystemView = fileSystemView;
		panel.setFocusable(false);
		panel.setRequestFocusEnabled(false);
		panel.setOpaque(false);
		panel.add(checkBox, BorderLayout.WEST);
		checkBox.setOpaque(false);
	}

	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded,
			boolean leaf, int row, boolean hasFocus) {
		JLabel l = (JLabel) renderer.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
		l.setFont(tree.getFont());
		if (value instanceof DefaultMutableTreeNode) {
			checkBox.setEnabled(tree.isEnabled());
			checkBox.setFont(tree.getFont());
			Object userObject = ((DefaultMutableTreeNode) value).getUserObject();
			if (userObject instanceof CheckBoxNode) {
				CheckBoxNode node = (CheckBoxNode) userObject;
				if (node.status == Status.INDETERMINATE) {
					checkBox.setIcon(new IndeterminateIcon());
				} else {
					checkBox.setIcon(null);
				}
				File file = (File) node.file;
				l.setIcon(fileSystemView.getSystemIcon(file));
				l.setText(fileSystemView.getSystemDisplayName(file));
				l.setToolTipText(file.getPath());
				checkBox.setSelected(node.status == Status.SELECTED);
			}
			panel.add(l);
			return panel;
		}
		return l;
	}
}

class CheckBoxNodeEditor extends AbstractCellEditor implements TreeCellEditor {
	private final JPanel panel = new JPanel(new BorderLayout());
	private final DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
	private final TriStateCheckBox checkBox = new TriStateCheckBox();
	private final FileSystemView fileSystemView;
	private File file;
	
	private BinaryList list = new BinaryList();

	protected CheckBoxNodeEditor(FileSystemView fileSystemView) {
		super();
		this.fileSystemView = fileSystemView;
		checkBox.setOpaque(false);
		checkBox.setFocusable(false);
		checkBox.addActionListener(e -> stopCellEditing());

		checkBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				list.add(file.toString());
				try {
					CSVUtils.writeIAList(list.getList());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		panel.setFocusable(false);
		panel.setRequestFocusEnabled(false);
		panel.setOpaque(false);
		panel.add(checkBox, BorderLayout.WEST);
	}

	@Override
	public Component getTreeCellEditorComponent(JTree tree, Object value, boolean selected, boolean expanded,
			boolean leaf, int row) {
		JLabel l = (JLabel) renderer.getTreeCellRendererComponent(tree, value, true, expanded, leaf, row, true);
		l.setFont(tree.getFont());
		if (value instanceof DefaultMutableTreeNode) {
			checkBox.setEnabled(tree.isEnabled());
			checkBox.setFont(tree.getFont());
			Object userObject = ((DefaultMutableTreeNode) value).getUserObject();
			if (userObject instanceof CheckBoxNode) {
				CheckBoxNode node = (CheckBoxNode) userObject;
				if (node.status == Status.INDETERMINATE) {
					checkBox.setIcon(new IndeterminateIcon());
				} else {
					checkBox.setIcon(null);
				}
				file = node.file;
				l.setIcon(fileSystemView.getSystemIcon(file));
				l.setText(fileSystemView.getSystemDisplayName(file));
				checkBox.setSelected(node.status == Status.SELECTED);
			}
			panel.add(l);
			return panel;
		}
		return l;
	}

	@Override
	public Object getCellEditorValue() {
		return new CheckBoxNode(file, checkBox.isSelected() ? Status.SELECTED : Status.DESELECTED);
	}

	@Override
	public boolean isCellEditable(EventObject e) {
		if (e instanceof MouseEvent && e.getSource() instanceof JTree) {
			Point p = ((MouseEvent) e).getPoint();
			JTree tree = (JTree) e.getSource();
			TreePath path = tree.getPathForLocation(p.x, p.y);
			return Optional.ofNullable(tree.getPathBounds(path)).map(r -> {
				r.width = checkBox.getPreferredSize().width;
				return r.contains(p);
			}).orElse(false);

		}
		return false;
	}
}

class FolderSelectionListener implements TreeSelectionListener {
	private final FileSystemView fileSystemView;

	protected FolderSelectionListener(FileSystemView fileSystemView) {
		this.fileSystemView = fileSystemView;
	}

	@Override
	public void valueChanged(TreeSelectionEvent e) {
		JTree tree = (JTree) e.getSource();
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) e.getPath().getLastPathComponent();

		if (!node.isLeaf()) {
			return;
		}
		CheckBoxNode check = (CheckBoxNode) node.getUserObject();
		if (Objects.isNull(check)) {
			return;
		}
		File parent = check.file;
		if (!parent.isDirectory()) {
			return;
		}
		Status parentStatus = check.status == Status.SELECTED ? Status.SELECTED : Status.DESELECTED;

		DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
		BackgroundTask worker = new BackgroundTask(fileSystemView, parent) {
			@Override
			protected void process(List<File> chunks) {

				for (File file : chunks) {
					model.insertNodeInto(new DefaultMutableTreeNode(new CheckBoxNode(file, parentStatus)), node,
							node.getChildCount());
				}
			}
		};
		worker.execute();
	}
}

class BackgroundTask extends SwingWorker<String, File> {
	private final FileSystemView fileSystemView;
	private final File parent;

	protected BackgroundTask(FileSystemView fileSystemView, File parent) {
		super();
		this.fileSystemView = fileSystemView;
		this.parent = parent;
	}

	@Override
	public String doInBackground() {
		Arrays.stream(fileSystemView.getFiles(parent, true)).forEach(this::publish);
		return "done";
	}
}