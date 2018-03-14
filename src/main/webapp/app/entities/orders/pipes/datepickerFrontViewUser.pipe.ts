import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
    name: 'datepickerFrontViewUser'
})
export class DatepickerFrontViewUserPipe implements PipeTransform {

    transform(dateObj: any) {
        if (dateObj.year && dateObj.month && dateObj.day) {
            const dateString = '' + ((('' + dateObj.day).length > 1) ? dateObj.day : ('0' + dateObj.day)) + '.'
                + ((('' + dateObj.month).length > 1) ? dateObj.month : ('0' + dateObj.month)) + '.'
                + dateObj.year;
            return dateString;
        }
        return dateObj;
    }
}
