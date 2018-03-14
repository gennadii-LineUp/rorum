import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
    name: 'datepickerFrontViewAdmin'
})
export class DatepickerFrontViewAdminPipe implements PipeTransform {

    transform(dateString: string) {
        if (dateString.indexOf('-')) {
            const dateArray = dateString.split('-');
            return '' + dateArray[2] + '.' + dateArray[1] + '.' + dateArray[0];
        }
        return dateString;
    }
}
