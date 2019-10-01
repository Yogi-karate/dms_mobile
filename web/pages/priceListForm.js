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
    MenuItem
} from "@material-ui/core";
import classnames from "classnames";
import { connect } from 'react-redux';
import { withStyles } from '@material-ui/styles';
import { getCompanies } from '../lib/api/dashboard';
import { priceListUpload } from '../lib/api/dashboard';
import { noAuto } from "@fortawesome/fontawesome-svg-core";

class PriceListForm extends React.Component {

    constructor(props) {
        super(props);
        this.props = props;
        console.log("dropdown props are", this.props);
        this.state = {
            name: '',
            companies: [],
            file: '',
            company: '',
        };
        this.handleNameChange = this.handleNameChange.bind(this);
        this.handleCompanyChange = this.handleCompanyChange.bind(this);
        this.handleFileChange = this.handleFileChange.bind(this);
    }

    static async getInitialProps(ctx) {
        const props = {};
        props.activeTabId = 0;
        console.log("The getInitialProps are ", props);
        return props;
    }

    onSubmitHandler = (e) => {
        console.log("Inside onSubmitHandler");
        e.preventDefault();
        this.submitPriceListForm();
    }

    handleNameChange(evt) {
        this.setState({ name: evt.target.value, error: "" });
    }

    handleCompanyChange = prop => event => {
        this.setState({ [prop]: event.target.value });
        this.setState({ company: event.target.value });
        console.log("The selected val is ", event.target.value);
        //this.props.company(event.target.value);
    };

    handleFileChange(evt) {
        this.setState({ file: evt.target.value, error: "" });
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

    async submitPriceListForm() {
        try {
            const formData = { name: this.state.name, company: this.state.company[1], file: this.state.file }
            const data = await priceListUpload(formData);
            console.log("The result is ", data);
        } catch (err) {
            console.log(err); // eslint-disable-line
        }
    }


    render() {
        const { classes } = this.props;
        const props = this.props;
        return (
            <Grid container className={classes.container}>
                {/* <div className={classes.logotypeContainer}>
                    <img src="/static/Saboo-02.png" alt="logo" className={classes.logotypeImage} />
                    <Typography className={classes.logotypeText}>DMS</Typography>
                </div> */}
                <div className={classes.formContainer}>
                    <div className={classes.form}>
                        <Tabs
                            value={props.activeTabId}
                            onChange={props.handleTabChange}
                            indicatorColor="primary"
                            textColor="primary"
                            centered
                        >
                            <Tab label="PRICELIST FORM" classes={{ root: classes.tab }} />
                        </Tabs>
                        {props.activeTabId === 0 && (
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
                                    accept="image/*"
                                    className={classes.uploadInput}
                                    id="raised-button-file"
                                    multiple
                                    type="file"
                                    onChange={this.handleFileChange}
                                    value={this.state.file}
                                />
                                <label htmlFor="raised-button-file">
                                    <Button variant="raised" component="span" className={classes.uploadButton}>
                                        Upload
                                    </Button>
                                </label>
                                <div className={classes.errorMessage}>{this.state.error}</div>
                                <div className={classes.formButtons}>
                                    {this.state.isLoading ? (
                                        <CircularProgress size={26} className={classes.loginLoader} />
                                    ) : (
                                            <Button
                                                disabled={
                                                    this.state.companies.length === 0 ||
                                                    this.state.name.length === 0 ||
                                                    this.state.file.length === 0
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
                        )}
                    </div>
                    <Typography color="primary" className={classes.copyright}>
                        © Copyright  2018-2019 , TurnRight Private Ltd.
                    </Typography>
                </div>
            </Grid>
        )
    }
};

const styles = theme => ({
    container: {
        height: "100vh",
        width: "100vw",
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        position: "absolute",
        top: 0,
        left: 0
    },
    logotypeContainer: {
        backgroundColor: "#212121",
        width: "60%",
        height: "100%",
        display: "flex",
        flexDirection: "column",
        justifyContent: "center",
        alignItems: "center",
        [theme.breakpoints.down("md")]: {
            width: "50%"
        },
        [theme.breakpoints.down("md")]: {
            display: "none"
        }
    },
    formContainer: {
        width: "40%",
        height: "100%",
        display: "flex",
        flexDirection: "column",
        justifyContent: "center",
        alignItems: "center",
        [theme.breakpoints.down("md")]: {
            width: "50%"
        }
    },
    form: {
        width: 320
    },
    tab: {
        fontWeight: 400,
        fontSize: 18
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
        }
    },
    textField: {
        borderBottomColor: theme.palette.background.light
    },
    formButtons: {
        width: "100%",
        marginTop: theme.spacing(4),
        marginLeft: theme.spacing(12),
        display: "flex",
        justifyContent: "space-between",
        alignItems: "center"
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
        padding: "10px 0px",
    }
});
const mapStateToProps = state => {
    //const {app_state} = state;     
    console.log("state in mapping", state);
    return { user: state.user };
}

export default connect(
    mapStateToProps,
    null
)(withStyles(styles)(PriceListForm));