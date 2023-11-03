import {AppUserDTO} from "../../user/model/models";
import {TemplateModel} from "../../template/model/models";

export type Mail = {
    id: number;
    conversation : MailText[];
    sender: AppUserDTO;
    receiver: AppUserDTO;
    templateResponse : TemplateModel;
    read : boolean;
}

export type MailText = {
    id: number;
    text: string;
    senderMassage : boolean;
}

export type MessageDTO = {
    text: string;
    originalSender : boolean;
}

export type NewMail =
{
    text: string;
    templateId?: number;
    receiverId: number;

}