/*
   Copyright 2013 Mauricio Teixeira

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */

package com.wordpress.mteixeira.warrantychecker.dell;

import java.util.Date;

/**
 * Created by mteixeir on 7/19/13.
 */
public class Warranty {
    public Date EndDate;
    public String EntitlementType;
    public String ItemNumber;
    public String ServiceLevelCode;
    public String ServiceLevelDescription;
    public int ServiceLevelGroup;
    public String ServiceProvider;
    public Date StartDate;

    public void setEndDate(Date endDate) {
        this.EndDate = endDate;
    }

    public String getEntitlementType() {
        return EntitlementType;
    }

    public void setEntitlementType(String entitlementType) {
        this.EntitlementType = entitlementType;
    }

    public String getItemNumber() {
        return ItemNumber;
    }

    public void setItemNumber(String itemNumber) {
        this.ItemNumber = itemNumber;
    }

    public String getServiceLevelCode() {
        return ServiceLevelCode;
    }

    public void setServiceLevelCode(String serviceLevelCode) {
        this.ServiceLevelCode = serviceLevelCode;
    }

    public String getServiceLevelDescription() {
        return ServiceLevelDescription;
    }

    public void setServiceLevelDescription(String serviceLevelDescription) {
        this.ServiceLevelDescription = serviceLevelDescription;
    }

    public int getServiceLevelGroup() {
        return ServiceLevelGroup;
    }

    public void setServiceLevelGroup(int serviceLevelGroup) {
        this.ServiceLevelGroup = serviceLevelGroup;
    }

    public String getServiceProvider() {
        return ServiceProvider;
    }

    public void setServiceProvider(String serviceProvider) {
        this.ServiceProvider = serviceProvider;
    }

    public Date getStartDate() {
        return StartDate;
    }

    public void setStartDate(Date startDate) {
        this.StartDate = startDate;
    }

    public Date getEndDate() {
        return EndDate;
    }
}
