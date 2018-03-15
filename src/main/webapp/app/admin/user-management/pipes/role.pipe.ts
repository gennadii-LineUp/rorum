import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
    name: 'rolePipe'
})
export class RolePipe implements PipeTransform {
    roleName = [
        {value: 'USER_FILLING', display: 'Użytkownik wypełniający'},
        {value: 'ADMIN', display: 'Administrator'},
    ];

    transform(value: string) {
        const view = (this.roleName.filter((select) => {
            return select.value === value;
        }))['0'].display;
        return view;
    }
}
