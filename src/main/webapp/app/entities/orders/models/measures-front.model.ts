export class MeasuresFrontClass {
    checked: boolean;
    frequencyOfGatheringData: string;
    id: number;
    name: string;
    unitsOfMeasurement: string;

    constructor(checked: boolean,
                frequencyOfGatheringData: string,
                id: number,
                name: string,
                unitsOfMeasurement: string) {

        this.checked = checked;
        this.frequencyOfGatheringData = frequencyOfGatheringData;
        this.id = id;
        this.name = name;
        this.unitsOfMeasurement = unitsOfMeasurement;
    }
}
