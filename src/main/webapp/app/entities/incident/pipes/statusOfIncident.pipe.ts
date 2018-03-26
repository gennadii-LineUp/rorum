import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
    name: 'statusOfIncidentPipe'
})
export class StatusOfIncidentPipe implements PipeTransform {
    select_costs_values = [
        {value: 'REPORTED', display: 'Zgłoszony'},
        {value: 'SUPERVISED', display: 'Monitorowany'},
    ];

    transform(value: string) {
        const view = (this.select_costs_values.filter((select) => {
            return select.value === value;
        }))['0'].display;
        return view;
    }
}
