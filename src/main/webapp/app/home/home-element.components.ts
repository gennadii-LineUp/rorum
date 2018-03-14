import {Component, Input} from '@angular/core';

@Component({
    selector: 'jhi-home-element',
    template: `
    <div class="col-md-12">
        <div class="alert alert-info">
            <a [routerLink]="[routerLink]" class="text-center">
                <i [ngClass]="['fa', faIcon, 'fa-4x']" aria-hidden="true" style="width: 100%;"></i>
                <div class="row">
                    <span style="width: 100%;">{{name}}</span>
                </div>
            </a>
        </div>
    </div>
  `
})
export class HomeElementComponent {

    @Input()
    routerLink;
    @Input()
    name;
    @Input()
    faIcon;

}
