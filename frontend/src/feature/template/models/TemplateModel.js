import PropTypes from "prop-types";

export const TemplateModel = PropTypes.shape(
{
        id: PropTypes.number.isRequired,
        name: PropTypes.string.isRequired,
        description: PropTypes.string.isRequired,
        defaultCount: PropTypes.number.isRequired,
        createdBy : PropTypes.string.isRequired,
        image: PropTypes.string.isRequired,
        stampCardCategory: PropTypes.string.isRequired,
        stampCardSecurity: PropTypes.string.isRequired,
        stampCardStatus: PropTypes.string.isRequired,
        createdDate : PropTypes.string.isRequired,
        lastModifiedDate : PropTypes.string.isRequired,
    }).isRequired;