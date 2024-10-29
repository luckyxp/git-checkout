package com.xp.gitcheckout.action;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import git4idea.GitLocalBranch;
import git4idea.branch.GitBrancher;
import git4idea.repo.GitRepository;
import git4idea.repo.GitRepositoryManager;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.util.Collections;

public class CleanLocalBranchAction extends AnAction {
    private final GitRepositoryManager git;
    private final GitBrancher gitBrancher;

    private final Project project;

    public CleanLocalBranchAction(Project project) {
        super("Clean Local Branch", "Clean local branch", AllIcons.Actions.GC);
        this.project = project;
        git = GitRepositoryManager.getInstance(project);
        gitBrancher = GitBrancher.getInstance(project);
    }
    @Override
    public void actionPerformed(@NotNull AnActionEvent anActionEvent) {

        DialogWrapper dialog = new DialogWrapper(project, true) {
            {
                setTitle("Clean All Local Branch");
                init();
            }
            @Override
            protected JComponent createCenterPanel() {
                JPanel panel = new JPanel(new BorderLayout());
                panel.add(new JLabel("clean all local branch ?"), BorderLayout.CENTER);
                return panel;
            }
        };

        dialog.show();
        if (dialog.isOK()) {
            System.out.println("clean");
            for (GitRepository repository : git.getRepositories()) {
                for (GitLocalBranch branch : repository.getBranches().getLocalBranches()) {
                    if (branch.getName().equals(repository.getCurrentBranchName())) {
                        continue;
                    }
                    gitBrancher.deleteBranch(branch.getName(), Collections.singletonList(repository));
                }
            }
        }
    }
}
