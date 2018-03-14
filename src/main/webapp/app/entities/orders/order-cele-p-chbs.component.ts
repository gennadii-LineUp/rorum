import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {CheckboxesFromSelectDataModel} from './models/checkboxes-from-select-data.model';

@Component({
    selector: 'jhi-popup-checkboxes',
    templateUrl: './order-cele-p-chbs.component.html',
    styleUrls: ['./order-cele-p-chbs.component.css']
})
export class OrderCelePopupCheckboxesComponent implements OnInit {
    purposesToCheck = [];
    _dataIncome: Array<CheckboxesFromSelectDataModel>;
    _checkboxesChecked: Array<number>;
    _disadledMode: boolean;
    @Input() title: string;
    @Input() set checkboxesChecked(checkboxesChecked: Array<number>) {
        this._checkboxesChecked = checkboxesChecked;
        this.purposesToCheck = this._checkboxesChecked;
        // console.log(this._checkboxesChecked);
    }
    @Input() set dataIncome(data: Array<CheckboxesFromSelectDataModel>) {
        this._dataIncome = data;
        // console.log(this._dataIncome);
    }
    @Input() set disadledMode(disadledMode: boolean) {
        this._disadledMode = disadledMode;
    }
    @Output() onClosePopup = new EventEmitter<boolean>();
    @Output() dataOutput = new EventEmitter<Array<number>>();

    constructor() {}

    ngOnInit() {}

    public addCryterium(e: any) {
        if (!this._disadledMode) {
            const minId = this._dataIncome['0'].id;
            const maxId = this._dataIncome[this._dataIncome.length - 1].id;
            // console.log(minId + ' -:- ' + maxId);
            const userInput = e.target;
            if (userInput.checked) {
                this.purposesToCheck = this.purposesToCheck.filter((elem) => {
                    return (minId <= elem) && (elem <= maxId);
                });
                this.purposesToCheck.push(+userInput.id);
                this.purposesToCheck = this.purposesToCheck.filter((elem, index, self) => {
                    return index === self.indexOf(elem);
                });
            }
            if (!userInput.checked) {
                this.purposesToCheck = this.purposesToCheck.filter((val) => val !== +userInput.id);
            }
            this.purposesToCheck.sort(function(first, second) {
                return first - second;
            });
            // console.log(this.purposesToCheck);
            this.dataOutput.emit(this.purposesToCheck);
        } else {console.log(')'); }
    }

    closePopup() {
        // console.log('close popup');
        this.onClosePopup.emit(true);
    }

}
