package com.xp.gitcheckout.action;

import com.intellij.openapi.actionSystem.ActionUpdateThread;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.xp.gitcheckout.BranchTree;
import com.xp.gitcheckout.settings.GitCheckoutSettings;
import com.xp.gitcheckout.settings.GitCheckoutState;
import org.jetbrains.annotations.NotNull;

import javax.swing.tree.DefaultMutableTreeNode;

public abstract class AbstractTreeAction extends AnAction {

    protected final GitCheckoutState settings;

    protected final BranchTree tree;
    public AbstractTreeAction(String name,BranchTree tree) {
        super(name);
        this.tree = tree;
        settings = GitCheckoutSettings.settings(tree.getProject());
    }

    protected DefaultMutableTreeNode[] selected(AnActionEvent e) {
        return tree.getSelectedNodes(DefaultMutableTreeNode.class, node -> node.getParent() != null);
    }

    @Override
    public @NotNull ActionUpdateThread getActionUpdateThread() {
        return ActionUpdateThread.EDT;
    }
}
