<template>
  <div :id="id" :class="className" :style="{height:height,width:width}"/>
</template>

<script>
import echarts from 'echarts'
import resize from './mixins/resize'
import { fetchFilteredVisData } from '@/api/libraryData'

export default {
  mixins: [resize],
  props: {
    className: {
      type: String,
      default: 'chart'
    },
    id: {
      type: String,
      default: 'chart'
    },
    width: {
      type: String,
      default: '200px'
    },
    height: {
      type: String,
      default: '200px'
    }
  },
  data() {
    return {
      chart: null,
      chartData: {
        xData: [],
        yData1: [],
        yData2: [],
        yData3: [],
        yData4: [],
        yDatat: []
      }
    }
  },
  mounted() {
    this.fetchDataAndInitChart()
  },
  beforeDestroy() {
    if (!this.chart) {
      return
    }
    this.chart.dispose()
    this.chart = null
  },
  methods: {
    fetchDataAndInitChart() {
      const xData = []
      const yData1 = []
      const yData2 = []
      const yData3 = []
      const yData4 = []
      const yData = []

      Promise.all([
        fetchFilteredVisData(),
        fetchFilteredVisData({ floorNumber: 1 }),
        fetchFilteredVisData({ floorNumber: 2 }),
        fetchFilteredVisData({ floorNumber: 3 }),
        fetchFilteredVisData({ floorNumber: 4 })
      ]).then(responses => {
        const [allFloorsResponse, floor1Response, floor2Response, floor3Response, floor4Response] = responses

        // Process allFloorsResponse
        allFloorsResponse.forEach(item => {
          xData.push(item.time)
          yData.push(item.totalSeats) // Assuming you want total seats here
        })

        // Process floor1Response
        floor1Response.forEach(item => {
          yData1.push(item.emptySeats)
        })

        // Process floor2Response
        floor2Response.forEach(item => {
          yData2.push(item.emptySeats)
        })

        // Process floor3Response
        floor3Response.forEach(item => {
          yData3.push(item.emptySeats)
        })

        // Process floor4Response
        floor4Response.forEach(item => {
          yData4.push(item.emptySeats)
        })

        this.chartData.xData = xData
        this.chartData.yData1 = yData1
        this.chartData.yData2 = yData2
        this.chartData.yData3 = yData3
        this.chartData.yData4 = yData4
        this.chartData.yDatat = yData
        this.initChart()
        // Now you have all your data processed
      }).catch(error => {
        console.error('Error fetching data:', error)
      })
    },

    initChart() {
      this.chart = echarts.init(document.getElementById(this.id))

      this.chart.setOption({
        backgroundColor: '#344b58',
        title: {
          text: 'statistics',
          x: '20',
          top: '20',
          textStyle: {
            color: '#fff',
            fontSize: '22'
          },
          subtextStyle: {
            color: '#90979c',
            fontSize: '16'
          }
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            textStyle: {
              color: '#fff'
            }
          }
        },
        grid: {
          left: '5%',
          right: '5%',
          borderWidth: 0,
          top: 150,
          bottom: 95,
          textStyle: {
            color: '#fff'
          }
        },
        legend: {
          x: '5%',
          top: '10%',
          textStyle: {
            color: '#90979c'
          },
          data: ['1st floor', '2nd floor', '3rd floor', '4th floor', 'Total seats']
        },
        calculable: true,
        xAxis: [{
          type: 'category',
          axisLine: {
            lineStyle: {
              color: '#90979c'
            }
          },
          splitLine: {
            show: false
          },
          axisTick: {
            show: false
          },
          splitArea: {
            show: false
          },
          axisLabel: {
            interval: 0

          },
          data: this.chartData.xData
        }],
        yAxis: [{
          type: 'value',
          splitLine: {
            show: false
          },
          axisLine: {
            lineStyle: {
              color: '#90979c'
            }
          },
          axisTick: {
            show: false
          },
          axisLabel: {
            interval: 0
          },
          splitArea: {
            show: false
          }
        }],
        dataZoom: [{
          show: true,
          height: 30,
          xAxisIndex: [
            0
          ],
          bottom: 30,
          start: 10,
          end: 80,
          handleIcon: 'path://M306.1,413c0,2.2-1.8,4-4,4h-59.8c-2.2,0-4-1.8-4-4V200.8c0-2.2,1.8-4,4-4h59.8c2.2,0,4,1.8,4,4V413z',
          handleSize: '110%',
          handleStyle: {
            color: '#d3dee5'

          },
          textStyle: {
            color: '#fff'
          },
          borderColor: '#90979c'

        }, {
          type: 'inside',
          show: true,
          height: 15,
          start: 1,
          end: 35
        }],
        series: [{
          name: '1st floor',
          type: 'bar',
          stack: 'total',
          itemStyle: {
            normal: {
              color: 'rgba(0,191,183,1)',
              barBorderRadius: 0,
              label: {
                show: true,
                position: 'top',
                formatter(p) {
                  return p.value > 0 ? p.value : ''
                }
              }
            }
          },
          data: this.chartData.yData1
        }, {
          name: '2nd floor',
          type: 'bar',
          stack: 'total',
          itemStyle: {
            normal: {
              color: 'rgb(0,41,191)',
              barBorderRadius: 0,
              label: {
                show: true,
                position: 'top',
                formatter(p) {
                  return p.value > 0 ? p.value : ''
                }
              }
            }
          },
          data: this.chartData.yData2
        }, {
          name: '3rd floor',
          type: 'bar',
          stack: 'total',
          itemStyle: {
            normal: {
              color: 'rgb(191,22,0)',
              barBorderRadius: 0,
              label: {
                show: true,
                position: 'top',
                formatter(p) {
                  return p.value > 0 ? p.value : ''
                }
              }
            }
          },
          data: this.chartData.yData3
        }, {
          name: '4th floor',
          type: 'bar',
          stack: 'total',
          itemStyle: {
            normal: {
              color: 'rgb(19,191,0)',
              barBorderRadius: 0,
              label: {
                show: true,
                position: 'top',
                formatter(p) {
                  return p.value > 0 ? p.value : ''
                }
              }
            }
          },
          data: this.chartData.yData4
        }, {
          name: 'total seats',
          type: 'line',
          stack: 'total',
          symbolSize: 10,
          symbol: 'circle',
          itemStyle: {
            normal: {
              color: 'rgba(252,230,48,1)',
              barBorderRadius: 0,
              label: {
                show: true,
                position: 'top',
                formatter(p) {
                  return p.value > 0 ? p.value : ''
                }
              }
            }
          },
          data: this.chartData.yDatat
        }
        ]
      })
    }
  }
}
</script>
