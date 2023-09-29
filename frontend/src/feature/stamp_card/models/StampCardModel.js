import PropTypes from "prop-types";
import { TemplateModel } from "../../template/models/TemplateModel";

export const StampCardModel = PropTypes.shape(
    {
        id: PropTypes.number.isRequired,
        createdDate: PropTypes.string.isRequired,
        templateModel: TemplateModel,
    }).isRequired;