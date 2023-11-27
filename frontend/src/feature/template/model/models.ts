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

export type NewTemplateComposer = {

    newBasicInformation : NewBasicInformation;
    newTemplateImage : NewTemplateImage;
    newTemplateSecurity : NewTemplateSecurity;
}

export type NewBasicInformation = {
    name : string;
    promise : string;
    description : string;
    defaultCount : number;
    stampCardCategory : string;
}

export type NewTemplateImage = {
    image : string;
}

export type NewTemplateSecurity = {
    expirationDate : Date;
    securityTimeGateDurationInHour : number;
    stampCardSecurity : string;
    stampCardStatus : string;
}