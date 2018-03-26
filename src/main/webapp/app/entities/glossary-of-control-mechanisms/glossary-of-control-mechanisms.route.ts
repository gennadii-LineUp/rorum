import {UserRouteAccessService} from '../../shared';
import {GlossaryOfControlMechanismsComponent} from "./glossary-of-control-mechanisms.component";
import {Routes} from "@angular/router";

export const glossaryOfControlMechanismsRoute: Routes = [
    {
        path: 'glossary-of-control-mechanisms',
        component: GlossaryOfControlMechanismsComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Słownik Mechanizmów kontrolnych'
        },
        canActivate: [UserRouteAccessService]
    }
];
