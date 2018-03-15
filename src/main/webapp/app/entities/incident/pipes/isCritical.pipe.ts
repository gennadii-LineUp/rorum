import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
    name: 'isCriticalPipe'
})
export class IsCriticalPipe implements PipeTransform {

    transform(value: boolean) {
        return (value) ? 'Tak' : 'Nie';
    }
}
