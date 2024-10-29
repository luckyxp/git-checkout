package com.xp.gitcheckout.action;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.xp.gitcheckout.dialog.AddCommonBranchDialog;
import com.xp.gitcheckout.BranchTree;

public class AddCommonBranchAction extends AnAction {

    private final BranchTree tree;

    public AddCommonBranchAction(BranchTree tree) {
        super("Add Common Branch", "Add common branch", AllIcons.Actions.AddList);
        this.tree = tree;
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        // TODO: insert action logic here
        AddCommonBranchDialog dialog = new AddCommonBranchDialog(e.getProject(), true);
        dialog.show();
        if (dialog.isOK()) {
            tree.refresh(0);
        }
    }
}
