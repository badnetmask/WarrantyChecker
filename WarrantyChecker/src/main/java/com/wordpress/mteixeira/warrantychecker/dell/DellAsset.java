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

/**
 * Created by mteixeir on 7/19/13.
 */
public class DellAsset {
    public AssetParts AssetParts;
    public int CountryLookupCode;
    public int CustomerNumber;
    public String IsDuplicate;
    public String ItemClassCode;
    public String LocalChannel;
    public String MachineDescription;
    public int OderNumber;
    public ParentServiceTag ParentServiceTag;
    public String ServiceTag;
    public String ShipDate;
    public Warranties Warranties;

    public AssetParts getAssetParts() {
        return AssetParts;
    }

    public void setAssetParts(AssetParts assetParts) {
        this.AssetParts = assetParts;
    }

    public int getCountryLookupCode() {
        return CountryLookupCode;
    }

    public void setCountryLookupCode(int countryLookupCode) {
        this.CountryLookupCode = countryLookupCode;
    }

    public int getCustomerNumber() {
        return CustomerNumber;
    }

    public void setCustomerNumber(int customerNumber) {
        this.CustomerNumber = customerNumber;
    }

    public String getIsDuplicate() {
        return IsDuplicate;
    }

    public void setIsDuplicate(String isDuplicate) {
        IsDuplicate = isDuplicate;
    }

    public String getItemClassCode() {
        return ItemClassCode;
    }

    public void setItemClassCode(String itemClassCode) {
        this.ItemClassCode = itemClassCode;
    }

    public String getLocalChannel() {
        return LocalChannel;
    }

    public void setLocalChannel(String localChannel) {
        this.LocalChannel = localChannel;
    }

    public String getMachineDescription() {
        return MachineDescription;
    }

    public void setMachineDescription(String machineDescription) {
        this.MachineDescription = machineDescription;
    }

    public int getOderNumber() {
        return OderNumber;
    }

    public void setOderNumber(int oderNumber) {
        this.OderNumber = oderNumber;
    }

    public ParentServiceTag getParentServiceTag() {
        return ParentServiceTag;
    }

    public void setParentServiceTag(ParentServiceTag parentServiceTag) {
        this.ParentServiceTag = parentServiceTag;
    }

    public String getServiceTag() {
        return ServiceTag;
    }

    public void setServiceTag(String serviceTag) {
        this.ServiceTag = serviceTag;
    }

    public String getShipDate() {
        return ShipDate;
    }

    public void setShipDate(String shipDate) {
        this.ShipDate = shipDate;
    }

    public void setWarranties(Warranties warranties) {
        this.Warranties = warranties;
    }

    public Warranties getWarranties() {
        return Warranties;
    }
}