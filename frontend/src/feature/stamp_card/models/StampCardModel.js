import PropTypes from "prop-types";

export const StampCardModel = PropTypes.shape(
    {
        id: PropTypes.number.isRequired,
        name: PropTypes.string.isRequired,
        description: PropTypes.string.isRequired,

    }).isRequired;