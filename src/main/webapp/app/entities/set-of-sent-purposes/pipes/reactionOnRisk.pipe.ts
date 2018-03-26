
import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
    name: 'reactionOnRiskPipe'
})
export class ReactionOnRiskPipe implements PipeTransform {
    selectCR_reactionOnRisks_values = [
        {value: undefined, display: ''},
        {value: 'ACCEPTATION_RISK', display: 'Akceptacja ryzyka'},
        {value: 'SHARE_RISK', display: 'Dzielenie się ryzykiem'},
        {value: 'AVOID_RISK', display: 'Unikanie ryzyka'},
        {value: 'RESTRICT_RISK', display: 'Ograniczenie ryzyka'},
        {value: 'REINFORCEMENT', display: 'Wzmocnienie'},
        {value: 'USAGE', display: 'Wykorzystanie'}
    ];


    transform(name: String) {
        const option = this.selectCR_reactionOnRisks_values.filter((obj) =>  obj.value === name);
        return option['0'].display;
    }
}
