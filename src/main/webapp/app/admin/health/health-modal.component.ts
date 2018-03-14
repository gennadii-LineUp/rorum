import {Component} from '@angular/core';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';

import {JhiHealthService} from './health.service';

@Component({
    selector: 'jhi-health-modal',
    templateUrl: './health-modal.component.html'
})
export class JhiHealthModalComponent {

    currentHealth: any;

    constructor(private healthService: JhiHealthService, public activeModal: NgbActiveModal) {}

    baseName(name) {
        return this.healthService.getBaseName(name);
    }

    subSystemName(name) {
        return this.healthService.getSubSystemName(name);
    }

    readableValue(baseValue: number) {
        if (this.currentHealth.name !== 'diskSpace') {
            return baseValue.toString();
        }

        // Should display storage space in an human readable unit
        const val = baseValue / 1073741824;
        if (val > 1) { // Value
            return val.toFixed(2) + ' GB';
        } else {
            return (baseValue / 1048576).toFixed(2) + ' MB';
        }
    }
}
