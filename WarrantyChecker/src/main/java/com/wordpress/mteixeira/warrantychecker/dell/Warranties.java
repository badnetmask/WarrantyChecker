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

import java.util.List;

/**
 * Created by mteixeir on 7/19/13.
 */
public class Warranties {
    public List<Warranty> Warranty;

    public void setWarranty(List<Warranty> Warranty) {
        this.Warranty = Warranty;
    }

    public List<Warranty> getWarranty() {
        return Warranty;
    }
}