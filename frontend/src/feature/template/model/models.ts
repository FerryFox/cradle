import {AppUserDTO} from "../../user/model/models";

export type TemplateModel = {
    id: number;

    name : string;
    promise : string;
    description : string;
    defaultCount : number;

    stampCardCategory : string;
    stampCardSecurity : string;
    stampCardStatus : string;

    image : string;

    createdBy : string;
    createdDate : string;
    creator? : AppUserDTO;
    userId : number;

    lastModifiedDate : string;
    expirationDate : Date;
};

export type NewTemplate = {
    name : string;
    promise : string;
    description : string;
    defaultCount : number;

    stampCardCategory : string;
    stampCardSecurity : string;
    stampCardStatus : string;

    image : string;
    fileName : string;

    expirationDate : Date;

    securityTimeGate : SecurityTimeGate;
}

export type SecurityTimeGate = {
    timeGateNumber : number;
}