/*
 *  Copyright (c) 2015, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *  WSO2 Inc. licenses this file to you under the Apache License,
 *  Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 *
 */
package org.wso2.carbon.analytics.dataservice;

import org.wso2.carbon.analytics.dataservice.clustering.AnalyticsClusterManager;

import com.hazelcast.core.HazelcastInstance;
import org.wso2.carbon.application.deployer.Feature;
import org.wso2.carbon.user.core.service.RealmService;

import java.util.List;
import java.util.Map;

/**
 * This represents a service holder class for analytics data service.
 */
public class AnalyticsServiceHolder {

    private static HazelcastInstance hazelcastInstance;
    
    private static AnalyticsClusterManager analyticsClusterManager;
    
    private static AnalyticsDataService analyticsDataService;

    private static RealmService realmService;

    private static Map<String, List<Feature>> requiredFeatures;

    public static void setHazelcastInstance(HazelcastInstance hazelcastInstance) {
        AnalyticsServiceHolder.hazelcastInstance = hazelcastInstance;
    }
    
    public static HazelcastInstance getHazelcastInstance() {
        return hazelcastInstance;
    }

    public static AnalyticsClusterManager getAnalyticsClusterManager() {
        return analyticsClusterManager;
    }

    public static void setAnalyticsClusterManager(AnalyticsClusterManager analyticsClusterManager) {
        AnalyticsServiceHolder.analyticsClusterManager = analyticsClusterManager;
    }
    
    public static AnalyticsDataService getAnalyticsDataService() {
        return analyticsDataService;
    }
    
    public static void setAnalyticsDataService(AnalyticsDataService analyticsDataService) {
        AnalyticsServiceHolder.analyticsDataService = analyticsDataService;
    }

    public static void setRealmService(RealmService realmService) {
        AnalyticsServiceHolder.realmService = realmService;
    }

    public static RealmService getRealmService() {
        return AnalyticsServiceHolder.realmService;
    }

    public static Map<String, List<Feature>> getRequiredFeatures() {
        return requiredFeatures;
    }

    public static void setRequiredFeatures(Map<String, List<Feature>> requiredFeatures) {
        AnalyticsServiceHolder.requiredFeatures = requiredFeatures;
    }
}
