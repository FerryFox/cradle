import {AppUserDTO} from "../../user/model/models";

export type BlogDTO =
{
    id? : number;
    title : string;
    content : string;
    createdDate : Date;
    pictureBase64 : string;
    appUser?: AppUserDTO;
}