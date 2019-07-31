import { createStore, applyMiddleware } from 'redux'
import { composeWithDevTools } from 'redux-devtools-extension'

const exampleInitialState = {
  lastUpdate: 0,
  light: false,
  count: 0,
  user: null,
  team:null,
  enquiry_stage:null,
}

export const actionTypes = {
  TICK: 'TICK',
  INCREMENT: 'INCREMENT',
  DECREMENT: 'DECREMENT',
  RESET: 'RESET',
  LOGIN: 'LOGIN',
  TEAM_CHANGE:'TEAM',
  ENQUIRY_STAGE:'ENQUIRY_STAGE'
}

// REDUCERS
export const reducer = (state = exampleInitialState, action) => {
  switch (action.type) {
    case actionTypes.TICK:
      return Object.assign({}, state, {
        lastUpdate: action.ts,
        light: !!action.light
      })
    case actionTypes.INCREMENT:
      return Object.assign({}, state, {
        count: state.count + 1
      })
    case actionTypes.DECREMENT:
      return Object.assign({}, state, {
        count: state.count - 1
      })
    case actionTypes.RESET:
      return Object.assign({}, state, {
        count: exampleInitialState.count
      })
    case actionTypes.LOGIN:
      return Object.assign({}, state, {
        user: action.user
      })
      case actionTypes.TEAM_CHANGE:
      return Object.assign({}, state, {
        team: action.team
      })
      case actionTypes.ENQUIRY_STAGE:
      return Object.assign({}, state, {
        enquiry_stage: action.stage
      })
      
    default:
      return state
  }
}

// ACTIONS
export const login = (user) => {
  console.log("Trying store login");
  return { type: actionTypes.LOGIN, user:user }
}
export const team = (team) => {
  console.log("Trying to change team");
  return { type: actionTypes.TEAM_CHANGE, team:team }
}
export const enquiry_stage_change = (stage) => {
  console.log("Trying to change enq stage");
  return { type: actionTypes.ENQUIRY_STAGE, stage:stage }
}
export const serverRenderClock = () => {
  return { type: actionTypes.TICK, light: false, ts: Date.now() }
}
export const startClock = () => {
  return { type: actionTypes.TICK, light: true, ts: Date.now() }
}

export const incrementCount = () => {
  return { type: actionTypes.INCREMENT }
}

export const decrementCount = () => {
  return { type: actionTypes.DECREMENT }
}

export const resetCount = () => {
  return { type: actionTypes.RESET }
}

export function initializeStore(initialState = exampleInitialState) {
  return createStore(
    reducer,
    initialState,
    composeWithDevTools(applyMiddleware())
  )
}
