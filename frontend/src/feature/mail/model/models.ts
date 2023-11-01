import {AppUserDTO} from "../../user/model/models";
import {TemplateModel} from "../../template/model/models";

export type Mail = {
    id: number;
    text: string;
    sender: AppUserDTO;
    templateResponse : TemplateModel;
    read : boolean;
}