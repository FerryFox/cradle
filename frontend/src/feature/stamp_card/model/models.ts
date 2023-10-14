import {TemplateModel} from "../../template/model/models";

export type StampCardModel =
{
    id: number;
    createdDate: string;
    templateModel: TemplateModel;
    stampFields: StampFieldModel[];

    isCompleted: boolean;
    isRedeemed: boolean;
};


export type StampFieldModel =
{
    id: number;
    isStamped: boolean;
    emptyImageUrl: string;
    stampedImageUrl: string;
    index: number;
    stampCardId : number;
}




