import React, { Component } from "react";
import { Bar } from '@vx/shape';
import { Group } from '@vx/group';
import { GradientTealBlue,LinearGradient,GradientLightgreenGreen } from '@vx/gradient';
import { letterFrequency } from '@vx/mock-data';
import { scaleBand, scaleLinear } from '@vx/scale';
import { AxisLeft, AxisBottom } from '@vx/axis';


const data = letterFrequency.slice(5);

// accessors
const x = d => d.letter;
const y = d => +d.frequency * 100;

const width = 714;
const height = 300;
  // bounds
  const xMax = width;
  const yMax = height - 120;

  // scales
  const xScale = scaleBand({
    rangeRound: [0, xMax],
    domain: data.map(x),
    padding: 0.4
  });
  const yScale = scaleLinear({
    rangeRound: [yMax, 0],
    domain: [0, Math.max(...data.map(y))]
  });

// Compose together the scale and accessor functions to get point functions
const compose = (scale, accessor) => (data) => scale(accessor(data));
const xPoint = compose(xScale, x);
const yPoint = compose(yScale, y);

class TRGraph extends Component {

    
    render() {
        return (
            <svg width={width} height={height}>
            {/* <GradientTealBlue id="teal" /> */}
            <LinearGradient from="#FFFFFF" to="#FFFFFF" id="black"/>;
            <rect width={width} height={height} fill={"url(#black)"} rx={14} />
            <Group top={40}>
              <AxisLeft
                scale={yScale}
                top={20}
                left={17}
                label={'Enquiries'}
                stroke={'#FF0000'}
                tickTextFill={'#FF0000'}
              />
              <AxisBottom
                  scale={xScale}
                  top={yMax}
                  label={'Years'}
                  stroke={'#FF0000'}
                  tickTextFill={'#FF0000'}
                />
              {data.map((d, i) => {
                const letter = x(d);
                const barWidth = xScale.bandwidth();
                const barHeight = yMax - yScale(y(d));
                const barX = xScale(letter);
                const barY = yMax - barHeight;
                return (
                  <Bar
                    key={`bar-${letter}`}
                    x={barX}
                    y={barY}
                    width={barWidth}
                    height={barHeight}
                    // fill="rgba(23, 233, 217, .7)"
                    fill="rgba(206, 162, 229, .7)"
                    onClick={event => {
                      alert(`clicked: ${JSON.stringify(Object.values(d))}`);
                    }}
                  />
                );
              })}
            </Group>
          </svg>
            );
        }
    }

export default TRGraph; 