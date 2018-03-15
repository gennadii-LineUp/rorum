import {NgModule} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {HttpModule} from '@angular/http';
import {CommonModule} from '@angular/common';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {NgJhipsterModule} from 'ng-jhipster';
import {InfiniteScrollModule} from 'ngx-infinite-scroll';
import {CookieModule} from 'ngx-cookie';
import {DataTableModule} from 'primeng/components/datatable/datatable';
import {TooltipModule} from 'primeng/components/tooltip/tooltip';
import {ProgressSpinnerModule} from 'primeng/components/progressspinner/progressspinner';
import {BlockUIModule} from 'primeng/components/blockui/blockui';
import {GrowlModule} from 'primeng/components/growl/growl';
import {ConfirmDialogModule} from 'primeng/components/confirmdialog/confirmdialog';
import {DialogModule} from 'primeng/components/dialog/dialog';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {DropdownModule} from 'primeng/components/dropdown/dropdown';
import {TabViewModule} from 'primeng/components/tabview/tabview';
import {OrganizationChartModule} from "primeng/components/organizationchart/organizationchart";

@NgModule({
    imports: [
        NgbModule.forRoot(),
        NgJhipsterModule.forRoot({
            // set below to true to make alerts look like toast
            alertAsToast: false,
            i18nEnabled: true,
            defaultI18nLang: 'pl'
        }),
        InfiniteScrollModule,
        CookieModule.forRoot(),
        DataTableModule,
        TooltipModule,
        ProgressSpinnerModule,
        BlockUIModule,
        GrowlModule,
        DialogModule,
        BrowserAnimationsModule,
        DropdownModule,
        TabViewModule,
        OrganizationChartModule,
    ],
    exports: [
        FormsModule,
        HttpModule,
        CommonModule,
        NgbModule,
        NgJhipsterModule,
        InfiniteScrollModule,
        DataTableModule,
        TooltipModule,
        ProgressSpinnerModule,
        BlockUIModule,
        GrowlModule,
        DialogModule,
        BrowserAnimationsModule,
        DropdownModule,
        TabViewModule,
        OrganizationChartModule,
    ]
})
export class RorumSharedLibsModule {}
