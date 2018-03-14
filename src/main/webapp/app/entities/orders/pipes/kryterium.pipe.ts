import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
    name: 'removeWordKryterium'
})
export class KryteriumPipe implements PipeTransform {

    transform(value: any) {
        if (value.split(' ')['0'] === 'KRYTERIUM') {
            return value.split('KRYTERIUM ')['1'];
        }
        return value;
    }
}
