import React from "react";
import {
    Grid,
    CircularProgress,
    Typography,
    Button,
    Tabs,
    Tab,
    TextField,
    Fade,
    MenuItem,
    Slide,
    Icon
} from "@material-ui/core";
import { connect } from 'react-redux';
import { withStyles } from '@material-ui/styles';
import { getCompanies, priceListUpload, createJobLog, getJobMaster, getJobLog, priceListItems } from '../../lib/api/dashboard';
import { priceListFileItems, showPriceListItems } from '../../lib/store';

class PriceListFormComponent extends React.Component {

    constructor(props) {
        super(props);
        this.props = props;
        console.log("dropdown props are", this.props);
        this.state = {
            name: '',
            companies: [],
            file: null,
            company: '',
            fileName: '',
            jobLogID: '',
            jobLogStatus: '',
            submitStatus: false,
            refreshStatus: ''
        };
        this.handleNameChange = this.handleNameChange.bind(this);
        this.handleCompanyChange = this.handleCompanyChange.bind(this);
        this.handleFileChange = this.handleFileChange.bind(this);
    }

    onSubmitHandler = (e) => {
        console.log("Inside onSubmitHandler");
        e.preventDefault();
        this.submitPriceListForm();
    }

    handleNameChange(evt) {
        this.setState({ name: evt.target.value });
    }

    handleCompanyChange = prop => event => {
        this.setState({ [prop]: event.target.value });
        this.setState({ company: event.target.value });
        console.log("The selected val is ", event.target.value);
        //this.props.company(event.target.value);
    };

    handleFileChange(evt) {
        console.log("The handleFileChange ", evt.target.files[0]);
        this.setState({ file: evt.target.files[0] });
        if (evt.target.files[0].name != undefined || evt.target.files[0].name != null) {
            this.setState({ fileName: evt.target.files[0].name });
        } else {
            this.setState({ fileName: 'empty' });
        }
    }

    onRefreshHandler = (e) => {
        console.log("Inside onRefreshHandler");
        e.preventDefault();
        this.fetchPriceListItems();
    }

    async componentDidMount() {
        console.log("Inside getting companiesss");
        this.props.showPriceListItems(false);//on display of submit page dont show pricelist table
        try {
            console.log("Printing the user ", this.props.user);
            const data = await getCompanies();
            console.log("The result is ", data);
            console.log("The result companies is ", data.records);
            if (data == null || data == [] || data.records == []) {
                this.setState({ companies: [], company: "" });
            } else {
                let result = data.records.map(company => {
                    return [company.name, company.id];
                })
                console.log("The result companies is ", result[0][1]);
                this.setState({ companies: result, company: result[0] });
                //this.props.company(result[0]);
            }
        } catch (err) {
            console.log(err); // eslint-disable-line
        }

    }

    async submitPriceListForm() {
        try {
            /* get the  jobMaster for priceList */
            const jobMaster = await getJobMaster("PriceList_Status");

            /* create job log */
            const jobLogBody = {
                "successCount": 0,
                "failedCount": 0,
                "status": "pending",
                "name": "PriceList",
                "jobMaster": jobMaster[0]._id
            };
            const jobLogCreated = await createJobLog(jobLogBody);
            console.log("The created jobLog ID is ", jobLogCreated._id);
            this.setState({ jobLogID: jobLogCreated._id });

            const formData = new FormData();
            formData.append('file', this.state.file);
            formData.append("name", this.state.name);
            formData.append("company", this.state.company[1]);
            formData.append("jobLogID", this.state.jobLogID);
            console.log("The formdata is ", formData);
            this.setState({ submitStatus: true });
            const data = await priceListUpload(formData);
        } catch (err) {
            console.log(err); // eslint-disable-line
        }
    }

    /* on refresh check the status for jobLog stored in state and if (state = success) fetch data from db */
    async fetchPriceListItems() {
        const formDataJobLogId = this.state.jobLogID;
        const fileName = this.state.name;
        console.log("The formDataJobLogId and fileName is ", formDataJobLogId, fileName);
        if (formDataJobLogId != null && formDataJobLogId != '' && fileName != null && fileName != '') {
            const jobLog = await getJobLog(formDataJobLogId);
            const jobLogStatus = jobLog[0].status;
            this.setState({ jobLogStatus: jobLogStatus });
            console.log("The fetchPriceListItems jobLogStatus is ", jobLogStatus);
            if (jobLogStatus === 'pending') {
                const data = await priceListItems(fileName);
                console.log("The result after submitPriceListForm from dataBase is  ", data);
                console.log("The arguments for priceListItems are ", this.state.name);
                if (data != null) {
                    this.setState({ refreshStatus: "success" });
                    this.props.priceListFileItems(data);
                    this.props.showPriceListItems(true);//show pricelist table
                } else {
                    this.setState({ refreshStatus: "No Data Found" });
                }
            } else {
                this.setState({ refreshStatus: "pending" });
            }
        } else {
            this.setState({ refreshStatus: "JobLog or FileName empty" });
        }
    }

    render() {
        console.log("The submittttt status is ", this.state.submitStatus);
        const { classes } = this.props;
        const props = this.props;
        return (
            <Grid container>
                <div className={classes.formContainer}>
                    {this.state.submitStatus ? (
                        <Slide direction="left" in={true} mountOnEnter unmountOnExit>

                            <div className={classes.form}>
                                <Tabs
                                    onChange={props.handleTabChange}
                                    indicatorColor="white"
                                    textColor="white"
                                    centered
                                >
                                    <Tab label="FORM DETAILS" className={classes.formTab} />
                                </Tabs>
                                <React.Fragment>
                                    <TextField
                                        id="name"
                                        label="Name"
                                        InputProps={{
                                            classes: {
                                                underline: classes.textFieldUnderline,
                                                input: classes.textField
                                            }
                                        }}
                                        defaultValue={this.state.name}
                                        margin="normal"
                                        fullWidth
                                        disabled
                                    />
                                    <TextField
                                        id="Company"
                                        label="Company"
                                        InputProps={{
                                            classes: {
                                                underline: classes.textFieldUnderline,
                                                input: classes.textField
                                            }
                                        }}
                                        defaultValue={this.state.company[0]}
                                        margin="normal"
                                        fullWidth
                                        disabled
                                    />
                                    <TextField
                                        id="fileName"
                                        label="FileName"
                                        InputProps={{
                                            classes: {
                                                underline: classes.textFieldUnderline,
                                                input: classes.textField
                                            }
                                        }}
                                        defaultValue={this.state.fileName}
                                        margin="normal"
                                        fullWidth
                                        disabled
                                    />
                                    <div className={classes.formButtons}>
                                        {this.state.isLoading ? (
                                            <CircularProgress size={26} className={classes.loginLoader} />
                                        ) : (
                                                <Button
                                                    disabled={
                                                        this.state.company.length === 0 ||
                                                        this.state.name.length === 0 ||
                                                        this.state.fileName.length === 0 ||
                                                        this.state.refreshStatus === "success" ||
                                                        this.state.refreshStatus === "No Data Found"
                                                    }
                                                    onClick={this.onRefreshHandler}
                                                    variant="contained"
                                                    color="secondary"
                                                    size="small"
                                                >
                                                    REFRESH
                                        </Button>
                                            )}
                                    </div>
                                    {this.state.refreshStatus !== '' ?
                                        (<span className={classes.refreshStatusName}><b>STATUS:</b> {this.state.refreshStatus}</span>) :
                                        (<span> </span>)
                                    }
                                </React.Fragment>
                            </div>
                        </Slide>) : (

                            <div className={classes.form}>
                                <Tabs
                                    onChange={props.handleTabChange}
                                    indicatorColor="white"
                                    textColor="white"
                                    centered
                                >
                                    <Tab label="PRICELIST FORM" className={classes.formTab} />
                                </Tabs>
                                <React.Fragment>
                                    <TextField
                                        id="name"
                                        InputProps={{
                                            classes: {
                                                underline: classes.textFieldUnderline,
                                                input: classes.textField
                                            }
                                        }}
                                        defaultValue={this.state.name}
                                        onChange={this.handleNameChange}
                                        margin="normal"
                                        placeholder="Enter the file name"
                                        type="string"
                                        fullWidth
                                    />
                                    <TextField
                                        select
                                        className={classes.textField}
                                        label="Select a Company"
                                        value={this.state.company}
                                        onChange={this.handleCompanyChange('company')}
                                        fullWidth
                                    >
                                        {this.state.companies.map(option => (
                                            <MenuItem key={option[1]} value={option}>
                                                {option[0]}
                                            </MenuItem>
                                        ))}
                                    </TextField>
                                    <input
                                        accept="*"
                                        className={classes.uploadInput}
                                        id="raised-button-file"
                                        multiple
                                        type="file"
                                        onChange={this.handleFileChange}
                                    />
                                    <label htmlFor="raised-button-file">
                                        <Button variant="raised" component="span" className={classes.uploadButton}
                                            variant="contained"
                                            size="small"
                                        >
                                            Upload
                                    </Button>
                                    </label>
                                    <span className={classes.uploadedFileName}>{this.state.fileName}</span>
                                    <div className={classes.formButtons}>
                                        {this.state.isLoading ? (
                                            <CircularProgress size={26} className={classes.loginLoader} />
                                        ) : (
                                                <Button
                                                    disabled={
                                                        this.state.companies.length === 0 ||
                                                        this.state.name.length === 0 ||
                                                        this.state.fileName.length === 0
                                                    }
                                                    onClick={this.onSubmitHandler}
                                                    variant="contained"
                                                    color="primary"
                                                    size="large"
                                                >
                                                    SUBMIT
                                        </Button>
                                            )}
                                    </div>
                                </React.Fragment>
                            </div>
                        )}
                </div>
            </Grid>
        )
    }
};

const styles = theme => ({
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
            boxShadow: "-2px -3px 9px 0 rgb(230, 27, 27)",
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
    }
});
const mapStateToProps = state => {
    //const {app_state} = state;     
    console.log("state in mapping", state);
    return { user: state.user };
}

const mapDispatchToProps = { priceListFileItems, showPriceListItems }

export default connect(
    mapStateToProps,
    mapDispatchToProps
)(withStyles(styles)(PriceListFormComponent));