const priceListStyles = (theme) =>({
    container: {
        height: "auto",
        width: "auto",
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        position: "absolute",
        top: 0,
        left: 0,
        background: "#ffffff"
    },
    formContainer: {
        width: "100%",
        height: "100%",
        display: "flex",
        flexDirection: "column",
        justifyContent: "center",
        alignItems: "center",
        [theme.breakpoints.down("md")]: {
            width: "50%"
        },
        margin: "50px"
    },
    form: {
        width: 500,
        padding: 50,
        boxShadow: "4px 4px 9px 0 rgb(9, 89, 165)",

        "&:hover": {
            boxShadow: "-2px -3px 9px 0 rgb(9, 89, 165)",
        }
    },
    formTab: {
        fontWeight: 400,
        fontSize: 48,
        background: "linear-gradient(45deg, #0066cc 30%, #0099ff 90%)",
        borderradius: "3px",
        border: 0,
        color: "white",
        height: "88px",
        padding: "0 30px",
        boxshadow: "0 3px 5px 2px rgba(255, 105, 135, 0.3)",
        minWidth: 900
    },
    errorMessage: {
        textAlign: "center",
        color: "red",
        fontWeight: 500,
        background: "#f5f5f5"
    },
    textFieldUnderline: {
        "&:before": {
            borderBottomColor: theme.palette.primary.light
        },
        "&:after": {
            borderBottomColor: theme.palette.primary.main
        },
        "&:hover:before": {
            borderBottomColor: `${theme.palette.primary.light} !important`
        },
        marginTop: "30px",
        marginBottom: "30px"
    },
    textField: {
        borderBottomColor: theme.palette.background.light,
        marginBottom: "10px"
    },
    formButtons: {
        width: "100%",
        marginTop: theme.spacing(4),
        marginLeft: theme.spacing(20),
        display: "flex",
        justifyContent: "space-between",
        alignItems: "center",
    },
    loginLoader: {
        marginLeft: theme.spacing(4),
    },
    copyright: {
        marginTop: theme.spacing(4),
        whiteSpace: 'nowrap',
        [theme.breakpoints.up("md")]: {
            position: "absolute",
            bottom: theme.spacing(2),
        }
    },
    uploadInput: {
        display: "none",
    },
    uploadButton: {
        marginTop: "27px",
        color: "green",
        background: "#ffffff"
    },
    uploadedFileName: {
        position: "relative",
        top: "18px",
        left: "15px",
        color: "#3b46d1"
    },
    refreshButton: {
        marginTop: "27px",
        color: "red",
        background: "#ffffff",
        float: "right"
    },
    refreshStatusName: {
        position: "relative",
        top: "10px",
        right: "146px",
        color: "#3b46d1",
        float: "right"
    },
    submitError: {
        position: "relative",
        top: "20px",
        right: "-136px",
        color: "#d92424",
    }
})

export default priceListStyles;