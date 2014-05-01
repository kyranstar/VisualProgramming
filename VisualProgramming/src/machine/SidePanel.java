package machine;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

import piece.Piece;
import piece.PieceGroup;

@SuppressWarnings("serial")
public class SidePanel extends JPanel {
	
	private static final int WIDTH = 200;
	final ProgrammingSpace space;
	JTree tree;
	public SidePanel(final ProgrammingSpace game){
		super();
		this.space = game;
		this.setPreferredSize(new Dimension(WIDTH, GamePanel.HEIGHT));
		tree = new JTree(createTree());
		tree.setRootVisible(false);
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		
		tree.addTreeSelectionListener(new TreeSelectionListener(){
			@Override
			public void valueChanged(TreeSelectionEvent e) {
				 DefaultMutableTreeNode node = (DefaultMutableTreeNode)
	                       tree.getLastSelectedPathComponent();

				 if (node == null)
					 return;

				 if (node.isLeaf() && node.getUserObject() instanceof Piece) {
					 Piece pieceCreated = PieceGroup.getInstanceOf(((Piece) node.getUserObject()).getClass(), space);
					 space.addPiece(pieceCreated);
					 tree.getSelectionModel().clearSelection();
				 }
			}
		});
		add( new JScrollPane(tree), BorderLayout.CENTER );
		super.setSize(tree.getSize());
	}
	public DefaultMutableTreeNode createTree(){
		//create the root node
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root");
		//create the child nodes
			for(PieceGroup group : PieceGroup.getGroups()){
				DefaultMutableTreeNode node = new DefaultMutableTreeNode(group.toString());
				for(Class<? extends Piece> p : group.getClasses()){
					node.add(new DefaultMutableTreeNode(PieceGroup.getInstanceOf(p, space)));
				}
				root.add(node);
			}
		return root;
	}
}
