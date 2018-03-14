import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
    name: 'trueFalseFrontView'
})
export class TrueFalseFrontViewPipe implements PipeTransform {

    transform(value: boolean) {
        return (value) ? 'Tak' : 'Nie';
    }
}
