import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
    name: 'probabilityValue'
})
export class ProbabilityValuePipe implements PipeTransform {
    selectCR_probability_values = [
        {value: 1, display: '1'},
        {value: 2, display: '2'},
        {value: 3, display: '3'},
        {value: 4, display: '4'},
        {value: 5, display: '5'},
    ];

    transform(id: number) {
            const option = this.selectCR_probability_values.filter((obj) =>  obj.value === id);
            console.log(option['0'].display);
            return option['0'].display.toLowerCase();
    }
}
