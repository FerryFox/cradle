import {TemplateModel} from "../../template/model/models";

export type StampCardModel =
{
    id: number;
    createdDate: string;
    templateModel: TemplateModel;
    stampFields: StampFieldModel[];

    completed: boolean;
    redeemed: boolean;
};


export type StampFieldModel =
{
    id: number;
    stamped: boolean;
    emptyImageUrl: string;
    stampedImageUrl: string;
    index: number;
    stampCardId : number;
}




