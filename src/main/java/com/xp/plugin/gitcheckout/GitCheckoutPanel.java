package com.xp.plugin.gitcheckout;

import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.SimpleToolWindowPanel;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.ui.components.JBScrollPane;
import com.xp.plugin.gitcheckout.action.AddCommonBranchAction;
import com.xp.plugin.gitcheckout.action.CleanLocalBranchAction;
import com.xp.plugin.gitcheckout.action.RefreshCommonBranchAction;
import java.awt.*;

public class GitCheckoutPanel extends SimpleToolWindowPanel {

    public GitCheckoutPanel(Project project, ToolWindow toolWindow) {
        super(true, true);
        System.out.println("GitCheckout create start");

        BranchTree tree = new BranchTree(project);

        ActionManager actionManager = ActionManager.getInstance();
        DefaultActionGroup actionGroup = new DefaultActionGroup();
        actionGroup.add(new AddCommonBranchAction(tree));
        actionGroup.add(new RefreshCommonBranchAction(tree));
        actionGroup.add(new CleanLocalBranchAction(project));
        ActionToolbar actionToolbar = actionManager.createActionToolbar("GitCheckoutToolbar", actionGroup, false);
        actionToolbar.setTargetComponent(this);
        this.add(actionToolbar.getComponent(), BorderLayout.WEST);

        JBScrollPane jbScrollPane = new JBScrollPane();

        jbScrollPane.setViewportView(tree);
        this.add(jbScrollPane, BorderLayout.CENTER);
        //MessageBusConnection connection = project.getMessageBus().connect();
        //connection.subscribe(GitRepository.GIT_REPO_CHANGE, (GitRepositoryChangeListener) repository -> tree.refresh(3000));
        System.out.println("GitCheckout create end");
    }
}
