package com.drife.digitaf.Service.PackageRule;

import com.drife.digitaf.retrofitmodule.Model.PackageRule;

import java.util.List;

public interface PackageRuleCallback {
    void onSuccessGetPackageRule(List<PackageRule> packageRules);
    void onFailedGetModePackageRule(String message);
}
