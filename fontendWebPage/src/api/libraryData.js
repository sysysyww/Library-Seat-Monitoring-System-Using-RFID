// api/libraryData.js
import request from '@/utils/request'

export function fetchFilteredVisData(queryParams) {
  return request({
    url: '/libraryData/dataVis',
    method: 'get',
    params: queryParams
  })
}

export function fetchFilteredVisDataReversed(queryParams) {
  return request({
    url: '/libraryData/dataCurrent',
    method: 'get',
    params: queryParams
  })
}

export function fetchTreeJson(queryParams) {
  return request({
    url: '/libraryData/TreeJson',
    method: 'get',
    params: queryParams
  })
}
