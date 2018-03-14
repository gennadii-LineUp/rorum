import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
    name: 'purposeAccomplishment'
})
export class PurposeAccomplishmentPipe implements PipeTransform {
    selectM_purposeAccomplished_values = [
        {value: undefined, display: ''},
        {value: '_1_25', display: ' 1-25'},
        {value: '_26_50', display: '26-50'},
        {value: '_51_99', display: '51-99'},
        {value: '_100', display: '100'}
    ];

    transform(name: String) {
        const option = this.selectM_purposeAccomplished_values.filter((obj) =>  obj.value === name);
        return option['0'].display.toLowerCase();
    }
}
