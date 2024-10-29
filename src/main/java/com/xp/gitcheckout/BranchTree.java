package com.xp.gitcheckout;

import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.ActionPlaces;
import com.intellij.openapi.actionSystem.ActionPopupMenu;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.project.Project;
import com.intellij.ui.JBColor;
import com.intellij.ui.treeStructure.Tree;
import com.intellij.util.Alarm;
import com.xp.gitcheckout.action.CheckoutAction;
import com.xp.gitcheckout.action.DelCommonBranchAction;
import com.xp.gitcheckout.action.EditBranchAction;
import com.xp.gitcheckout.action.EditMultiBranchAction;
import com.xp.gitcheckout.settings.Branch;
import com.xp.gitcheckout.settings.GitCheckoutSettings;
import com.xp.gitcheckout.settings.GitCheckoutState;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.*;

public class BranchTree extends Tree {
    private final DefaultTreeModel treeModel;
    private final DefaultMutableTreeNode root;
    private final GitCheckoutState settings;
    private final Alarm alarm;

    private final Project project;

    public Project getProject() {
        return project;
    }

    public BranchTree(Project project) {
        super();
        this.project = project;
        settings = GitCheckoutSettings.settings(project);
        alarm = new Alarm(Alarm.ThreadToUse.SWING_THREAD);
        ActionManager actionManager = ActionManager.getInstance();
        root = new DefaultMutableTreeNode("branch");
        treeModel = new DefaultTreeModel(root);
        this.setModel(treeModel);
        setRootVisible(false);

        setCellRenderer(new DefaultTreeCellRenderer() {
            @Override
            public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
                Component component = super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
                if (leaf && value instanceof DefaultMutableTreeNode node && node.getUserObject() instanceof Branch.Module module) {
                    JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
                    JLabel moduleName = new JLabel(module.name);
                    JLabel moduleBranch = new JLabel(module.branch);
                    moduleBranch.setForeground(JBColor.GRAY);
                    panel.add(moduleName);
                    panel.add(moduleBranch);
                    panel.setForeground(component.getForeground());
                    panel.setBackground(component.getBackground());
                    return panel;
                }
                return component;
            }
        });

        DefaultActionGroup group = new DefaultActionGroup();
        group.add(new CheckoutAction(this));
        group.add(new EditBranchAction(this));
        group.add(new EditMultiBranchAction(this));
        group.add(new DelCommonBranchAction(this));
        group.setPopup(true);
        ActionPopupMenu popupMenu = actionManager.createActionPopupMenu(ActionPlaces.MAIN_MENU, group);

        this.setComponentPopupMenu(popupMenu.getComponent());

        getSelectionModel().addTreeSelectionListener(e -> {
            TreePath path = e.getPath();
            if (path != null) {
                if (path.getLastPathComponent() instanceof DefaultMutableTreeNode node) {
                    if (node.isRoot()) {
                        clearSelection();
                    }
                }
            }
        });

        refresh(300);
    }


    public void refresh(long delay) {
        alarm.cancelAllRequests();
        alarm.addRequest(this::_refresh, delay);
    }

    private void _refresh() {
        System.out.println("GitCheckout refresh");
        root.removeAllChildren();
        //
        for (Branch branch : settings.branches) {
            DefaultMutableTreeNode node = new DefaultMutableTreeNode(branch);
            for (Branch.Module module : branch.modules) {
                DefaultMutableTreeNode moduleNode = new DefaultMutableTreeNode(module);
                node.add(moduleNode);
            }
            root.add(node);
        }
        treeModel.reload();
    }
}
