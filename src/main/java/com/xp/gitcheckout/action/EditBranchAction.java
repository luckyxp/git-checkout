package com.xp.gitcheckout.action;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.Presentation;
import com.xp.gitcheckout.BranchTree;
import com.xp.gitcheckout.dialog.EditBranchDialog;
import com.xp.gitcheckout.settings.Branch;
import org.jetbrains.annotations.NotNull;

import javax.swing.tree.DefaultMutableTreeNode;

public class EditBranchAction  extends AbstractTreeAction {
    public EditBranchAction(BranchTree tree) {
        super("Select Common Branch", tree);
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        DefaultMutableTreeNode[] selected = selected(e);
        DefaultMutableTreeNode node = selected[0];
        if (node != null) {
            if (node.getUserObject() instanceof Branch.Module module) {
                EditBranchDialog dialog = new EditBranchDialog(e.getProject(),module);
                dialog.show();
                // if (dialog.isOK()) {
                //     tree.refresh(300);
                // }
            }
        }
    }

    @Override
    public void update(@NotNull AnActionEvent e) {
        Presentation presentation = e.getPresentation();
        DefaultMutableTreeNode[] selected = selected(e);

        boolean visible = selected.length == 1 && selected[0].isLeaf();
        presentation.setVisible(visible);
    }
}
