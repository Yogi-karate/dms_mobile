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
    Icon,
    Fab
} from "@material-ui/core";
import RefreshIcon from '@material-ui/icons/Refresh';
import { connect } from 'react-redux';
import { withStyles } from '@material-ui/styles';
import { getCompanies, createJobLog, getJobMaster, getJobLog, saleDataUpload } from '../../lib/api/dashboard';
import saleDataStyles from '../../lib/styles/saleDataStyles';

class SaleDataFormComponent extends React.Component {

    constructor(props) {
        super(props);
        this.props = props;
        console.log("dropdown props are", this.props);
        this.state = {
            companies: [],
            file: null,
            company: '',
            fileName: '',
            jobLogID: '',
            jobLogStatus: '',
            submitStatus: false,
            refreshStatus: '',
            submitError: ''
        };
        this.handleCompanyChange = this.handleCompanyChange.bind(this);
        this.handleFileChange = this.handleFileChange.bind(this);
    }

    onSubmitHandler = (e) => {
        console.log("Inside onSubmitHandler");
        e.preventDefault();
        this.submitSaleDataForm();
    }

    onReloadHandler = (e) => {
        console.log("Inside onReloadHandler");
        e.preventDefault();
        window.location.reload();
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
        this.fetchSaleDataItems();
    }

    async componentDidMount() {
        console.log("Inside getting companiesss");
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

    async submitSaleDataForm() {
        try {
            /* get the  jobMaster for saleData */
            const jobMaster = await getJobMaster("SaleData");

            /* create job log */
            if (Array.isArray(jobMaster) && jobMaster.length > 0) {
                const jobLogBody = {
                    "successCount": 0,
                    "failedCount": 0,
                    "status": "pending",
                    "name": "SaleData",
                    "jobMaster": jobMaster[0]._id
                };
                const jobLogCreated = await createJobLog(jobLogBody);
                if (jobLogCreated._id != null && jobLogCreated._id != undefined) {
                    console.log("The created jobLog ID is ", jobLogCreated._id);
                    this.setState({ jobLogID: jobLogCreated._id });

                    const formData = new FormData();
                    formData.append('file', this.state.file);
                    formData.append("company", this.state.company[1]);
                    formData.append("jobLogID", this.state.jobLogID);
                    console.log("The formdata is ", formData);
                    this.setState({ submitStatus: true });
                    const data = await saleDataUpload(formData);
                } else {
                    console.log("Error creating job log in submitSaleDataForm method");
                    this.setState({ submitError: "Something went wrong" });
                }
            } else {
                console.log("Error fetching job master in submitSaleDataForm method");
                this.setState({ submitError: "Something went wrong" });
            }
        } catch (err) {
            console.log("inside catch submitSaleDataForm", err); // eslint-disable-line
            this.setState({ submitError: "Something went wrong" });
        }
    }

    /* on refresh check the status for jobLog stored in state and if (state = success) fetch data from db */
    async fetchSaleDataItems() {
        try {
            const formDataJobLogId = this.state.jobLogID;
            if (formDataJobLogId != null && formDataJobLogId != '') {
                console.log("The formDataJobLogId is ", formDataJobLogId);
                const jobLog = await getJobLog(formDataJobLogId);
                const jobLogStatus = jobLog[0].status;
                this.setState({ jobLogStatus: jobLogStatus });
                console.log("The fetchSaleDataItems jobLogStatus is ", jobLogStatus);
                if (jobLogStatus === 'success') {
                    this.setState({ refreshStatus: "success" });
                } else {
                    this.setState({ refreshStatus: "pending..." });
                }
            } else {
                this.setState({ refreshStatus: "JobLog empty" });
            }
        } catch (err) {
            this.setState({ refreshStatus: "Something went wrong" });
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
                                <Tab label="FORM DETAILS" className={classes.formTab} />
                                <React.Fragment>
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
                                                        this.state.fileName.length === 0 ||
                                                        this.state.refreshStatus === "success" ||
                                                        this.state.refreshStatus === "No Data Found"
                                                    }
                                                    onClick={this.onRefreshHandler}
                                                    variant="contained"
                                                    color="primary"
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
                                    <Fab
                                        className={classes.reloadButton}
                                        color="secondary"
                                        aria-label="refresh"
                                        size="small"
                                        title="reload form"
                                        onClick={this.onReloadHandler}
                                    >
                                        <RefreshIcon />
                                    </Fab>
                                </React.Fragment>
                            </div>
                        </Slide>) : (

                            <div className={classes.form}>
                                <Tab label="SALEDATA FORM" className={classes.formTab} />
                                <React.Fragment>
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
                                    <span className={classes.submitError}><b>{this.state.submitError}</b></span>
                                    <Fab
                                        className={classes.reloadButton}
                                        color="secondary"
                                        aria-label="refresh"
                                        size="small"
                                        title="reload form"
                                        onClick={this.onReloadHandler}
                                    >
                                        <RefreshIcon />
                                    </Fab>
                                </React.Fragment>
                            </div>
                        )}
                </div>
            </Grid>
        )
    }
};

const mapStateToProps = state => {
    //const {app_state} = state;     
    console.log("state in mapping", state);
    return { user: state.user };
}

export default connect(
    mapStateToProps,
    null
)(withStyles(saleDataStyles)(SaleDataFormComponent));