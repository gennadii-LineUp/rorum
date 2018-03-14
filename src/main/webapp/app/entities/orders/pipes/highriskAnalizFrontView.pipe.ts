import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
    name: 'highriskAnalizaView'
})
export class HighriskAnalizFrontViewPipe implements PipeTransform {
    select_costs_values = [
        {value: 'COSTS_MORE_BENEFITS', display: 'KOSZTY > KORZYŚCI'},
        {value: 'COSTS_ARE_EQUALED_TO_BENEFITS', display: 'KOSZTY = KORZYŚCI'},
        {value: 'COSTS_LESS_BENEFITS', display: 'KOSZTY < KORZYŚCI'}
    ];

    transform(value: string) {
        const view = (this.select_costs_values.filter((select) => {
            return select.value === value;
        }))['0'].display;
        return view;
    }
}
