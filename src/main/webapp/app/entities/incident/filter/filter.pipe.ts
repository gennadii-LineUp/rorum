import { Pipe, PipeTransform } from '@angular/core';
import {Incident} from "../incident.model";
import {forEach} from "@angular/router/src/utils/collection";
import {first} from "rxjs/operator/first";
@Pipe({
    name: 'filterTable'
})
export class FilterPipe implements PipeTransform {

    /**
     * @items = object from array
     * @term = term's search
     */
    transform(items: any, term: any, additional: any[][]): any {
        // console.log(additionalPoles);
        if (!term) {
            return items
        };
        return items.filter((item) => {
            for (let property in item) {
                // console.log(property)
                if (!item[property]) {
                    continue;
                }
                if (item[property].toString().toLowerCase().includes(term.toLowerCase())) {
                    return true;
                }

                if(property === 'statusOfIncident'){
                    if(item[property] === 'REPORTED' && 'Zgłoszony'.toLowerCase().includes(term.toLowerCase())){
                        return true
                    }
                    if(item[property] === 'SUPERVISED' && 'Nadzorowany'.toLowerCase().includes(term.toLowerCase())){
                        return true
                    }
                }

                for (let first in additional) {
                    if (additional[first] && additional[first].length === 3) {
                        if(item[additional[first][0]][additional[first][1]][additional[first][2]]
                                .toString().toLowerCase().includes(term.toLowerCase())) {
                            return true;
                        }
                    }
                    // let item2 = [];
                    // for (let second in additional[first]) {
                    //     // console.log(additional[first][second]);
                    // }

                }
            }

            return false;
        });
    }
}
