import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes} from '@angular/router';

import {UserRouteAccessService} from '../../shared';
import {GlossaryOfKRIComponent} from "./glossary-of-KRI.component";

export const glossaryOfKRIRoute: Routes = [
    {
        path: 'glossary-of-KRI',
        component: GlossaryOfKRIComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Słownik KRI'
        },
        canActivate: [UserRouteAccessService]
    }
];
