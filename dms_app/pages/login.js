import React from "react";
import {
    Grid,
    CircularProgress,
    Typography,
    Button,
    Tabs,
    Tab,
    TextField,
    Fade
} from "@material-ui/core";
import classnames from "classnames";
import { getLoginCreds } from '../lib/api/admin';
import Router from 'next/router';
import { login } from '../lib/store';
import { connect } from 'react-redux';
import { withStyles } from '@material-ui/styles';

class Login extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            mobile: '',
            password: '',
            error: '',
            isLoading: false,
        };
        this.handleMobileChange = this.handleMobileChange.bind(this);
        this.handlePinChange = this.handlePinChange.bind(this);
        this.handleEnterKey = this.handleEnterKey.bind(this);
    }

    static async getInitialProps(ctx) {
        const props = {};
        props.activeTabId = 0;
        props.loginValue = "";
        props.passwordValue = "";
        console.log("The getInitialProps are ", props);
        return props;
    }

    handleEnterKey = (e) => {
        if (e.key === 'Enter') {
            console.log("Inside handleEnterKey");
            e.preventDefault();
            this.checkForLoginUser();
        }
    }

    onSubmitHandler = (e) => {
        console.log("Inside onSubmitHandler");
        e.preventDefault();
        this.checkForLoginUser();
    }

    handleMobileChange(evt) {
        this.setState({ mobile: evt.target.value, error: "" });
    }

    handlePinChange(evt) {
        this.setState({ password: evt.target.value, error: "" });
    }

    async checkForLoginUser() {
        try {
            this.setState({ isLoading: true });
            const data = await getLoginCreds({"mobile":"1111111111","password":"admin:01"});
            console.log("The result is ", data);
            if (data.error === undefined || data.error === null || data.error === "") {
                console.log("user from backend api ",data);
                this.props.login(data);
                Router.push('/dashboard');
            } else {
                console.log("Wrong input", this.props.errorValue);
                this.setState({ error: "Email/Password incorrect" });
            }
        } catch (err) {
            console.log(err); // eslint-disable-line
            this.setState({ error: "Invalid Username or Password" });
        }
        this.setState({ isLoading: false });
    }

    render() {
        const { classes } = this.props;
        const props = this.props;
        return (
            <Grid container className={classes.container}>
                <div className={classes.logotypeContainer}>
                    <img src="/static/Saboo-02.png" alt="logo" className={classes.logotypeImage} />
                    <Typography className={classes.logotypeText}>DMS</Typography>
                </div>
                <div className={classes.formContainer}>
                    <div className={classes.form}>
                        <Tabs
                            value={props.activeTabId}
                            onChange={props.handleTabChange}
                            indicatorColor="primary"
                            textColor="primary"
                            centered
                        >
                            <Tab label="Welcome" classes={{ root: classes.tab }} />
                        </Tabs>
                        {props.activeTabId === 0 && (
                            <React.Fragment>
                                <TextField
                                    id="mobile"
                                    InputProps={{
                                        classes: {
                                            underline: classes.textFieldUnderline,
                                            input: classes.textField
                                        }
                                    }}
                                    defaultValue={this.state.mobile}
                                    onChange={this.handleMobileChange}
                                    onKeyDown={this.handleEnterKey}
                                    margin="normal"
                                    placeholder="Mobile Number"
                                    type="number"
                                    fullWidth
                                />
                                <TextField
                                    id="password"
                                    InputProps={{
                                        classes: {
                                            underline: classes.textFieldUnderline,
                                            input: classes.textField
                                        }
                                    }}
                                    defaultValue={this.state.password}
                                    onChange={this.handlePinChange}
                                    onKeyDown={this.handleEnterKey}
                                    margin="normal"
                                    placeholder="Password"
                                    type="password"
                                    fullWidth
                                />
                                <div className={classes.errorMessage}>{this.state.error}</div>
                                <div className={classes.formButtons}>
                                    {this.state.isLoading ? (
                                        <CircularProgress size={26} className={classes.loginLoader} />
                                    ) : (
                                            <Button
                                                /* disabled={
                                                   this.state.password.length === 0 ||
                                                   this.state.mobile.length === 0
                                               } */
                                                onClick={this.onSubmitHandler}
                                                variant="contained"
                                                color="primary"
                                                size="large"
                                            >
                                                Login
                                        </Button>
                                        )}
                                    <Button
                                        color="primary"
                                        size="large"
                                        className={classes.forgetButton}
                                    >
                                        Forget Password
                                    </Button>
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
    logotypeImage: {
        width: 165,
        marginBottom: theme.spacing(4),
    },
    logotypeText: {
        color: "white",
        fontWeight: 500,
        fontSize: 84,
        [theme.breakpoints.down("md")]: {
            fontSize: 48
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
    greeting: {
        fontWeight: 500,
        textAlign: "center",
        marginTop: theme.spacing(4),
        fontSize: 50
    },
    subGreeting: {
        fontWeight: 500,
        textAlign: "center",
        marginTop: theme.spacing(2),
    },
    googleButton: {
        marginTop: theme.spacing(6),
        //boxShadow: theme.customShadows.widget,
        backgroundColor: "white",
        width: "100%",
        textTransform: "none"
    },
    googleButtonCreating: {
        marginTop: 0
    },
    googleIcon: {
        width: 30,
        marginRight: theme.spacing(2),
    },
    creatingButtonContainer: {
        marginTop: theme.spacing(2.5),
        height: 46,
        display: "flex",
        justifyContent: "center",
        alignItems: "center"
    },
    createAccountButton: {
        height: 46,
        textTransform: "none"
    },
    formDividerContainer: {
        marginTop: theme.spacing(4),
        marginBottom: theme.spacing(4),
        display: "flex",
        alignItems: "center"
    },
    formDividerWord: {
        paddingLeft: theme.spacing(2),
        paddingRight: theme.spacing(2)
    },
    formDivider: {
        flexGrow: 1,
        height: 1,
        backgroundColor: theme.palette.text.hint + "40"
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
        display: "flex",
        justifyContent: "space-between",
        alignItems: "center"
    },
    forgetButton: {
        textTransform: "none",
        fontWeight: 400
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
    }
});
const mapStateToProps = state => {
    //const {app_state} = state;     
    console.log("state in mapping", state);
    return { user: state.user };
}
const mapDispatchToProps = { login }
export default connect(
    mapStateToProps,
    mapDispatchToProps
)(withStyles(styles)(Login));