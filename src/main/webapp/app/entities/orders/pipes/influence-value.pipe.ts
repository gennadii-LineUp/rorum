import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
    name: 'influenceValue'
})
export class InfluenceValuePipe implements PipeTransform {
    selectCR_influence_values = [
        {value: 1, display: '1'},
        {value: 2, display: '2'},
        {value: 3, display: '3'},
        {value: 4, display: '4'},
        {value: 5, display: '5'},
    ];

    transform(id: number) {
        const option = this.selectCR_influence_values.filter((obj) =>  obj.value === id);
        return option['0'].display.toLowerCase();
    }
}
