package com.xp.plugin.gitcheckout.action;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.xp.plugin.gitcheckout.BranchTree;

public class RefreshCommonBranchAction extends AnAction {

    private final BranchTree tree;

    public RefreshCommonBranchAction(BranchTree tree) {
        super("Refresh Common Branch", "Refresh common branch", AllIcons.Actions.Refresh);
        this.tree = tree;
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        // TODO: insert action logic here
        tree.refresh(0);
    }
}
