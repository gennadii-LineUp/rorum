export class RisksFrontClass {
    checked: boolean;
    completionDate: string;
    creationDate: string;
    id: number;
    importantTo: string;
    name: string;

    constructor(checked: boolean,
                completionDate: string,
                creationDate: string,
                id: number,
                importantTo: string,
                name: string) {

        this.checked = checked;
        this.completionDate = completionDate;
        this.creationDate = creationDate;
        this.id = id;
        this.importantTo = importantTo;
        this.name = name;
    }
}
